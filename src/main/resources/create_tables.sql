CREATE TABLE IF NOT EXISTS post (
  id              SERIAL PRIMARY KEY,
  create_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
  text            VARCHAR(255)             NOT NULL
);

CREATE TABLE IF NOT EXISTS comment (
  id              SERIAL PRIMARY KEY,
  create_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
  text            VARCHAR(255)             NOT NULL,
  post_id         INT                      NOT NULL,
  CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES post (id)
);
