package com.project.expensemanage.commone.redis.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.expensemanage.commone.security.dto.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@DataRedisTest
@Import({RedisRepository.class, ObjectMapper.class})
class RedisRepositoryTest {
  @Autowired RedisRepository repository;
  @Autowired ObjectMapper objectMapper;

  @Test
  @DisplayName("레디스 저장 테스트")
  void save_test() throws Exception {
    // given
    String key = "Test";
    //        String value = "Test";
    UserInfo data = new UserInfo("test", "TEST");
    String value = objectMapper.writeValueAsString(data);
    repository.save(key, value, 10);
    // when
    UserInfo result = objectMapper.readValue(repository.findByKey(key), UserInfo.class);
    // then
    assertThat(result).isInstanceOf(UserInfo.class);
    assertThat(result.getUserName()).isEqualTo(data.getUserName());
  }

  @Test
  @DisplayName("레디스 삭제 테스트")
  void delete_test() throws Exception {
    // given
    String key = "Test001";
    String value = "Test";
    // when
    repository.save(key, value, 10);
    repository.delete(key);
    Object result = repository.findByKey(key);
    // then
    assertThat(result).isNull();
  }

  @Test
  @DisplayName("레디스 동일한 key값에 value 연속 저장")
  void save_and_save_test() throws Exception {
    // given
    String key = "Test001";
    String value = "Test";
    // when
    repository.save(key, value, 10);
    value = "answerTest";
    repository.save(key, value, 10);
    Object result = repository.findByKey(key);
    // then
    assertThat(result).isEqualTo(value);
  }
}
