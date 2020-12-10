package pl.coderslab.blinddate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.entity.UserDetails;

import javax.transaction.Transactional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    @Modifying
    @Transactional
    void deleteAllByUser(User user);
    @Modifying
    @Transactional
    @Query("UPDATE UserDetails ud SET ud.age=:age, ud.description= :description, ud.gender= :gender, ud.name = :name, ud.orientation = :orientation WHERE ud.user= :user")
    void updateUserDetails(@Param("age") int age, @Param("description") String description, @Param("gender") char gender, @Param("name") String name, @Param("orientation") char orientation, @Param("user") User user);

}
