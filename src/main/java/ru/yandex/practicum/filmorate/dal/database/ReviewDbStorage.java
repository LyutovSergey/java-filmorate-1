package ru.yandex.practicum.filmorate.dal.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.dal.ReviewStorage;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.dal.database.sql.ReviewQueries.*;

@Repository
@Qualifier("reviewDbStorage")
public class ReviewDbStorage extends BaseDbStorage<Review> implements ReviewStorage {

    @Autowired
    public ReviewDbStorage(JdbcTemplate jdbc, @Qualifier("reviewRowMapper") RowMapper<Review> mapper) {
        super(jdbc, mapper);
    }

    @Override
    @Transactional
    public Review createReview(Review review) {
        long id = insertWithKeyHolder(
                SQL_REVIEWS_INSERT,
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId()
        );
        review.setId(id);
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        updateWithControl(
                SQL_REVIEWS_UPDATE,
                review.getContent(),
                review.getIsPositive(),
                review.getId()
        );
        return findReviewById(review.getId()).get();
    }

    @Override
    public void deleteReview(long id) {
        updateWithControl(SQL_REVIEWS_DELETE, id);
    }

    @Override
    public Optional<Review> findReviewById(long id) {
        return findOneByIdInTable(id, "reviews");
    }

    @Override
    public Collection<Review> findReviewsByFilmId(Long filmId, int count) {
        if (filmId == null) {
            return findManyByQuery(SQL_REVIEWS_FIND_ALL, count);
        }
        return findManyByQuery(SQL_REVIEWS_FIND_BY_FILM_ID, filmId, count);
    }

    @Override
    @Transactional
    public void addReviewLike(long id, long userId) {
        updateWithControl(SQL_REVIEWS_ADD_LIKE, id, userId);
        updateWithControl(SQL_REVIEWS_UPDATE_USEFUL, 1, id);
    }

    @Override
    @Transactional
    public void addReviewDislike(long id, long userId) {
        updateWithControl(SQL_REVIEWS_ADD_DISLIKE, id, userId);
        updateWithControl(SQL_REVIEWS_UPDATE_USEFUL, -1, id);
    }

    @Override
    @Transactional
    public void removeReviewLike(long id, long userId) {
        updateWithControl(SQL_REVIEWS_DELETE_LIKE, id, userId);
        updateWithControl(SQL_REVIEWS_UPDATE_USEFUL, -1, id);
    }

    @Override
    @Transactional
    public void removeReviewDislike(long id, long userId) {
        updateWithControl(SQL_REVIEWS_DELETE_DISLIKE, id, userId);
        updateWithControl(SQL_REVIEWS_UPDATE_USEFUL, 1, id);
    }

    @Override
    public boolean checkReviewIsNotPresent(long id){
        return checkIdIsNotPresentInTable(id, "reviews");
    }

    public Optional<Boolean> getReviewLikeOrDislike(long reviewId, long userId) {
        return jdbc.query(SQL_REVIEWS_GET_LIKE_OR_DISLIKE, (rs, rowNum) -> rs.getBoolean("is_like"), reviewId, userId)
                .stream()
                .findFirst();
    }
}
