CREATE TABLE message (
  id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  message_key VARCHAR(255) NOT NULL,
  sender VARCHAR(255) NOT NULL,
  saved_at DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX UNIQUE_KEY (message_key)
) ENGINE=INNODB DEFAULT CHARSET=utf8;