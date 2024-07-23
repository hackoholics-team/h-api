package app.hackoholics.api.service;

import app.hackoholics.api.model.User;
import app.hackoholics.api.model.exception.NotFoundException;
import app.hackoholics.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository repository;

  public User save(User user){
    return repository.save(user);
  }

  public User getById(String userId){
    return repository.findById(userId)
        .orElseThrow(()-> new NotFoundException("User.id="+userId+" is not found"));
  }
}
