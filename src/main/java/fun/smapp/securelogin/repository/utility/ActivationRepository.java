package fun.smapp.securelogin.repository.utility;

import fun.smapp.securelogin.model.utility.Activation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationRepository extends CrudRepository<Activation, Long> {
    public Activation findByActivationToken(String activationToken);
}
