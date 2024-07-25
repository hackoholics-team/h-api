package app.hackoholics.api.service.google.filestorage;

import app.hackoholics.api.service.google.GoogleConf;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BucketComponent {
  private final String bucketName;
  private final GoogleConf googleConf;

  public BucketComponent(@Value("${bucket.name}") String bucketName, GoogleConf conf) {
    this.bucketName = bucketName;
    this.googleConf = conf;
  }

  private Storage getStorage() {
    return StorageOptions.getDefaultInstance().toBuilder()
        .setCredentials(googleConf.getProjectCredentials())
        .build()
        .getService();
  }

  public String uploadFile(String fileId, byte[] file) throws IOException {
    BlobId id = BlobId.of(bucketName, fileId);
    BlobInfo fileInfo = BlobInfo.newBuilder(id).setContentType("image/*").build();
    Blob blob = getStorage().createFrom(fileInfo, new ByteArrayInputStream(file));
    return blob.asBlobInfo().getBlobId().getName();
  }

  public byte[] downloadFile(String fileId) {
    BlobId blobId = BlobId.of(bucketName, fileId);
    return getStorage().readAllBytes(blobId);
  }
}
