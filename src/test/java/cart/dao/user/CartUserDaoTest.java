package cart.dao.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Import(CartUserDao.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CartUserDaoTest {

    @Autowired
    private CartUserDao cartUserDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("사용자 이메일로 사용자 조회 테스트")
    @Test
    void findByEmail() {
        String findTargetEmail = "a@a.com";
        jdbcTemplate.update("INSERT INTO cart_user (email, cart_password)\n"
                + "values ('a@a.com', 'password1'),\n"
                + "       ('b@b.com', 'password2');");

        CartUserEntity user = cartUserDao.findByEmail(findTargetEmail);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(findTargetEmail);
    }

    @DisplayName("사용자 저장 테스트")
    @Test
    void insertCartUser() {
        String email = "email.com";
        CartUserEntity cartUserEntity
                = new CartUserEntity(email, "password");

        cartUserDao.insert(cartUserEntity);

        CartUserEntity findCartUser = cartUserDao.findByEmail(email);
        assertThat(findCartUser).isNotNull();
        assertThat(findCartUser.getEmail()).isEqualTo(email);
    }

    @DisplayName("사용자 전체 조회 테스트")
    @Test
    void findAllCartUser() {
        jdbcTemplate.update("INSERT INTO cart_user (email, cart_password)\n"
                + "values ('a@a.com', 'password1'),\n"
                + "       ('b@b.com', 'password2');");

        List<CartUserEntity> cartUserEntityList = cartUserDao.findAll();

        assertThat(cartUserEntityList).hasSize(2);
    }
}
