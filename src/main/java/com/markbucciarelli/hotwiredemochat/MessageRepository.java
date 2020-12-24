package com.markbucciarelli.hotwiredemochat;


import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
