INSERT INTO note (content) VALUES ('Hello world') ON CONFLICT (content) DO NOTHING;
INSERT INTO note (content) VALUES ('Welcome to Spring Boot!') ON CONFLICT (content) DO NOTHING;