package iti.student.foodo.data.db.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import iti.student.foodo.data.db.entity.UserEntity;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(UserEntity user);

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    Flowable<UserEntity> getUser(String userId);

    @Query("DELETE FROM users")
    Completable clearUsers();
}

