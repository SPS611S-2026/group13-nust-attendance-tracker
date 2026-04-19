-- ============================================================
-- Seed Data — NUST Attendance Tracker
-- Default password for ALL test accounts: password123
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- ============================================================

USE nust_attendance;

-- ── Roles ─────────────────────────────────────────────────
INSERT INTO roles (role_name) VALUES ('STUDENT'), ('LECTURER'), ('ADMIN')
ON DUPLICATE KEY UPDATE role_name = role_name;

-- ── Admin ─────────────────────────────────────────────────
INSERT INTO users (student_number, full_name, email, password_hash, role_id, is_active) VALUES
('10000001', 'System Administrator', 'admin@nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='ADMIN'), TRUE)
ON DUPLICATE KEY UPDATE full_name = full_name;

-- ── Lecturers ─────────────────────────────────────────────
INSERT INTO users (student_number, full_name, email, password_hash, role_id, is_active) VALUES
('10001001', 'Dr. Anna Shilongo',   'a.shilongo@nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='LECTURER'), TRUE),
('10001002', 'Mr. Jonas Hamutenya', 'j.hamutenya@nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='LECTURER'), TRUE)
ON DUPLICATE KEY UPDATE full_name = full_name;

-- ── Students (Group Members + extras) ────────────────────
INSERT INTO users (student_number, full_name, email, password_hash, role_id, is_active) VALUES
('223023434', 'Twapewoshinge Shooya', 't.shooya@student.nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='STUDENT'), TRUE),
('224066765', 'Ndati Kafidi',         'n.kafidi@student.nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='STUDENT'), TRUE),
('224016148', 'Jedidja Mbinga',       'j.mbinga@student.nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='STUDENT'), TRUE),
('224060533', 'Bernard Fotolela',     'b.fotolela@student.nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='STUDENT'), TRUE),
('224032119', 'Petrus Amukugo',       'p.amukugo@student.nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='STUDENT'), TRUE),
('223086770', 'Esegel Narib',         'e.narib@student.nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='STUDENT'), TRUE),
('224099001', 'Amalia Nghifikwa',     'a.nghifikwa@student.nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='STUDENT'), TRUE),
('224099002', 'Simon Iileka',         's.iileka@student.nust.na',
 '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 (SELECT role_id FROM roles WHERE role_name='STUDENT'), TRUE)
ON DUPLICATE KEY UPDATE full_name = full_name;

-- ── Modules ───────────────────────────────────────────────
INSERT INTO modules (module_code, module_name, lecturer_id) VALUES
('SPS611S', 'Software Processes',
 (SELECT user_id FROM users WHERE student_number='10001001')),
('CMP511S', 'Compiler Techniques',
 (SELECT user_id FROM users WHERE student_number='10001002')),
('INF521S', 'Information Systems',
 (SELECT user_id FROM users WHERE student_number='10001001'))
ON DUPLICATE KEY UPDATE module_name = module_name;

-- ── Enrollments ───────────────────────────────────────────
INSERT INTO enrollments (student_id, module_id) VALUES
-- SPS611S enrollments
((SELECT user_id FROM users WHERE student_number='223023434'), (SELECT module_id FROM modules WHERE module_code='SPS611S')),
((SELECT user_id FROM users WHERE student_number='224066765'), (SELECT module_id FROM modules WHERE module_code='SPS611S')),
((SELECT user_id FROM users WHERE student_number='224016148'), (SELECT module_id FROM modules WHERE module_code='SPS611S')),
((SELECT user_id FROM users WHERE student_number='224060533'), (SELECT module_id FROM modules WHERE module_code='SPS611S')),
((SELECT user_id FROM users WHERE student_number='224032119'), (SELECT module_id FROM modules WHERE module_code='SPS611S')),
((SELECT user_id FROM users WHERE student_number='223086770'), (SELECT module_id FROM modules WHERE module_code='SPS611S')),
-- CMP511S enrollments
((SELECT user_id FROM users WHERE student_number='223023434'), (SELECT module_id FROM modules WHERE module_code='CMP511S')),
((SELECT user_id FROM users WHERE student_number='224066765'), (SELECT module_id FROM modules WHERE module_code='CMP511S')),
((SELECT user_id FROM users WHERE student_number='224016148'), (SELECT module_id FROM modules WHERE module_code='CMP511S')),
((SELECT user_id FROM users WHERE student_number='224099001'), (SELECT module_id FROM modules WHERE module_code='CMP511S')),
((SELECT user_id FROM users WHERE student_number='224099002'), (SELECT module_id FROM modules WHERE module_code='CMP511S')),
-- INF521S enrollments
((SELECT user_id FROM users WHERE student_number='223023434'), (SELECT module_id FROM modules WHERE module_code='INF521S')),
((SELECT user_id FROM users WHERE student_number='224060533'), (SELECT module_id FROM modules WHERE module_code='INF521S')),
((SELECT user_id FROM users WHERE student_number='224032119'), (SELECT module_id FROM modules WHERE module_code='INF521S')),
((SELECT user_id FROM users WHERE student_number='224099001'), (SELECT module_id FROM modules WHERE module_code='INF521S'))
ON DUPLICATE KEY UPDATE enrolled_at = enrolled_at;

-- ── Sessions ──────────────────────────────────────────────
INSERT INTO sessions (module_id, session_date, session_name, qr_code_token, created_by) VALUES
((SELECT module_id FROM modules WHERE module_code='SPS611S'), '2026-03-03', 'Lecture 1 — Introduction',         UUID(), (SELECT user_id FROM users WHERE student_number='10001001')),
((SELECT module_id FROM modules WHERE module_code='SPS611S'), '2026-03-10', 'Lecture 2 — Process Models',       UUID(), (SELECT user_id FROM users WHERE student_number='10001001')),
((SELECT module_id FROM modules WHERE module_code='SPS611S'), '2026-03-17', 'Lecture 3 — Requirements',         UUID(), (SELECT user_id FROM users WHERE student_number='10001001')),
((SELECT module_id FROM modules WHERE module_code='SPS611S'), '2026-03-24', 'Lecture 4 — System Design',        UUID(), (SELECT user_id FROM users WHERE student_number='10001001')),
((SELECT module_id FROM modules WHERE module_code='SPS611S'), '2026-03-31', 'Lecture 5 — Agile & Scrum',        UUID(), (SELECT user_id FROM users WHERE student_number='10001001')),
((SELECT module_id FROM modules WHERE module_code='SPS611S'), '2026-04-07', 'Lecture 6 — Implementation',       UUID(), (SELECT user_id FROM users WHERE student_number='10001001')),
((SELECT module_id FROM modules WHERE module_code='CMP511S'), '2026-03-03', 'Lecture 1 — Lexical Analysis',     UUID(), (SELECT user_id FROM users WHERE student_number='10001002')),
((SELECT module_id FROM modules WHERE module_code='CMP511S'), '2026-03-10', 'Lecture 2 — Parsing',              UUID(), (SELECT user_id FROM users WHERE student_number='10001002')),
((SELECT module_id FROM modules WHERE module_code='CMP511S'), '2026-03-17', 'Lecture 3 — Semantic Analysis',    UUID(), (SELECT user_id FROM users WHERE student_number='10001002')),
((SELECT module_id FROM modules WHERE module_code='CMP511S'), '2026-03-24', 'Lecture 4 — Code Generation',      UUID(), (SELECT user_id FROM users WHERE student_number='10001002')),
((SELECT module_id FROM modules WHERE module_code='CMP511S'), '2026-03-31', 'Lecture 5 — Optimisation',         UUID(), (SELECT user_id FROM users WHERE student_number='10001002'));

-- ── Attendance Records ────────────────────────────────────
-- SPS611S (session ids 1-6) — varied attendance for realism
INSERT INTO attendance_records (session_id, student_id, status, marked_by) VALUES
-- Session 1
(1,(SELECT user_id FROM users WHERE student_number='223023434'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(1,(SELECT user_id FROM users WHERE student_number='224066765'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(1,(SELECT user_id FROM users WHERE student_number='224016148'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(1,(SELECT user_id FROM users WHERE student_number='224060533'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
(1,(SELECT user_id FROM users WHERE student_number='224032119'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(1,(SELECT user_id FROM users WHERE student_number='223086770'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
-- Session 2
(2,(SELECT user_id FROM users WHERE student_number='223023434'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(2,(SELECT user_id FROM users WHERE student_number='224066765'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(2,(SELECT user_id FROM users WHERE student_number='224016148'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
(2,(SELECT user_id FROM users WHERE student_number='224060533'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(2,(SELECT user_id FROM users WHERE student_number='224032119'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
(2,(SELECT user_id FROM users WHERE student_number='223086770'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
-- Session 3
(3,(SELECT user_id FROM users WHERE student_number='223023434'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(3,(SELECT user_id FROM users WHERE student_number='224066765'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(3,(SELECT user_id FROM users WHERE student_number='224016148'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(3,(SELECT user_id FROM users WHERE student_number='224060533'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(3,(SELECT user_id FROM users WHERE student_number='224032119'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
(3,(SELECT user_id FROM users WHERE student_number='223086770'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
-- Session 4
(4,(SELECT user_id FROM users WHERE student_number='223023434'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(4,(SELECT user_id FROM users WHERE student_number='224066765'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(4,(SELECT user_id FROM users WHERE student_number='224016148'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(4,(SELECT user_id FROM users WHERE student_number='224060533'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(4,(SELECT user_id FROM users WHERE student_number='224032119'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(4,(SELECT user_id FROM users WHERE student_number='223086770'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
-- Session 5
(5,(SELECT user_id FROM users WHERE student_number='223023434'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
(5,(SELECT user_id FROM users WHERE student_number='224066765'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(5,(SELECT user_id FROM users WHERE student_number='224016148'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(5,(SELECT user_id FROM users WHERE student_number='224060533'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
(5,(SELECT user_id FROM users WHERE student_number='224032119'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
(5,(SELECT user_id FROM users WHERE student_number='223086770'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
-- Session 6
(6,(SELECT user_id FROM users WHERE student_number='223023434'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(6,(SELECT user_id FROM users WHERE student_number='224066765'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(6,(SELECT user_id FROM users WHERE student_number='224016148'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(6,(SELECT user_id FROM users WHERE student_number='224060533'),'P',(SELECT user_id FROM users WHERE student_number='10001001')),
(6,(SELECT user_id FROM users WHERE student_number='224032119'),'A',(SELECT user_id FROM users WHERE student_number='10001001')),
(6,(SELECT user_id FROM users WHERE student_number='223086770'),'P',(SELECT user_id FROM users WHERE student_number='10001001'));
