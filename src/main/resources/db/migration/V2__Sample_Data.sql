-- Insert sample users (password is 'password123' hashed with BCrypt)
INSERT INTO users (username, email, password, first_name, last_name, role, active) VALUES
('admin', 'admin@example.com', '$2a$10$QKqF8VkQvXDWxJj/XVkGWuLTMfJYOmZxZvLdNwMYQPqLbKHUBqF2i', 'Admin', 'User', 'ADMIN', true),
('john.doe', 'john.doe@example.com', '$2a$10$QKqF8VkQvXDWxJj/XVkGWuLTMfJYOmZxZvLdNwMYQPqLbKHUBqF2i', 'John', 'Doe', 'USER', true),
('jane.smith', 'jane.smith@example.com', '$2a$10$QKqF8VkQvXDWxJj/XVkGWuLTMfJYOmZxZvLdNwMYQPqLbKHUBqF2i', 'Jane', 'Smith', 'USER', true);

-- Insert sample categories
INSERT INTO categories (name, description, color_code) VALUES
('Work', 'Work-related tasks', '#FF5733'),
('Personal', 'Personal tasks', '#33FF57'),
('Shopping', 'Shopping lists and errands', '#3357FF'),
('Health', 'Health and fitness tasks', '#FF33F5');

-- Insert sample tags
INSERT INTO tags (name) VALUES
('urgent'),
('important'),
('meeting'),
('review'),
('bug'),
('feature'),
('documentation');

-- Insert sample tasks
INSERT INTO tasks (title, description, status, priority, due_date, user_id, category_id) VALUES
('Complete project proposal', 'Finish the Q4 project proposal for client review', 'IN_PROGRESS', 'HIGH', CURRENT_TIMESTAMP + INTERVAL '3 days', 2, 1),
('Fix login bug', 'Resolve authentication issue in production', 'TODO', 'URGENT', CURRENT_TIMESTAMP + INTERVAL '1 day', 2, 1),
('Grocery shopping', 'Buy vegetables, fruits, and dairy products', 'TODO', 'MEDIUM', CURRENT_TIMESTAMP + INTERVAL '2 days', 2, 3),
('Morning workout', 'Complete 30-min cardio session', 'COMPLETED', 'MEDIUM', CURRENT_TIMESTAMP, 2, 4),
('Code review PR #123', 'Review changes for the new feature implementation', 'TODO', 'HIGH', CURRENT_TIMESTAMP + INTERVAL '1 day', 3, 1),
('Update documentation', 'Add API documentation for new endpoints', 'IN_PROGRESS', 'MEDIUM', CURRENT_TIMESTAMP + INTERVAL '5 days', 3, 1);

-- Link tasks with tags
INSERT INTO task_tags (task_id, tag_id) VALUES
(1, 2),
(2, 1),
(2, 5),
(3, 2),
(5, 3),
(5, 4),
(6, 7);
