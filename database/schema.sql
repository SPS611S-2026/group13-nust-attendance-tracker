-- ============================================================
-- NUST Digital Attendance Tracking System
-- Database Schema — MySQL 8
-- SPS611S Group Project 2026
-- ============================================================

CREATE DATABASE IF NOT EXISTS nust_attendance;
USE nust_attendance;

-- ── roles ─────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS roles (
    role_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- ── users ─────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS users (
    user_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_number VARCHAR(20)  NOT NULL UNIQUE,
    full_name      VARCHAR(100) NOT NULL,
    email          VARCHAR(150) NOT NULL UNIQUE,
    password_hash  VARCHAR(255) NOT NULL,
    role_id        BIGINT       NOT NULL,
    is_active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- ── modules ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS modules (
    module_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_code VARCHAR(20)  NOT NULL UNIQUE,
    module_name VARCHAR(150) NOT NULL,
    lecturer_id BIGINT       NOT NULL,
    CONSTRAINT fk_modules_lecturer FOREIGN KEY (lecturer_id) REFERENCES users(user_id)
);

-- ── enrollments ───────────────────────────────────────────
CREATE TABLE IF NOT EXISTS enrollments (
    enrollment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id    BIGINT   NOT NULL,
    module_id     BIGINT   NOT NULL,
    enrolled_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_enroll_student FOREIGN KEY (student_id) REFERENCES users(user_id),
    CONSTRAINT fk_enroll_module  FOREIGN KEY (module_id)  REFERENCES modules(module_id),
    CONSTRAINT uq_enrollment UNIQUE (student_id, module_id)
);

-- ── sessions ──────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS sessions (
    session_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    module_id     BIGINT       NOT NULL,
    session_date  DATE         NOT NULL,
    session_name  VARCHAR(100),
    qr_code_token VARCHAR(255) UNIQUE,
    created_by    BIGINT       NOT NULL,
    CONSTRAINT fk_session_module  FOREIGN KEY (module_id)  REFERENCES modules(module_id),
    CONSTRAINT fk_session_creator FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- ── attendance_records ────────────────────────────────────
CREATE TABLE IF NOT EXISTS attendance_records (
    record_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT   NOT NULL,
    student_id BIGINT   NOT NULL,
    status     ENUM('P','A') NOT NULL,
    marked_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    marked_by  BIGINT,
    CONSTRAINT fk_rec_session FOREIGN KEY (session_id) REFERENCES sessions(session_id),
    CONSTRAINT fk_rec_student FOREIGN KEY (student_id) REFERENCES users(user_id),
    CONSTRAINT fk_rec_marker  FOREIGN KEY (marked_by)  REFERENCES users(user_id),
    CONSTRAINT uq_attendance UNIQUE (session_id, student_id)
);

-- ── notifications ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS notifications (
    notification_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_id    BIGINT       NOT NULL,
    message         TEXT         NOT NULL,
    type            VARCHAR(50),
    sent_at         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_read         BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_notif_recipient FOREIGN KEY (recipient_id) REFERENCES users(user_id)
);

-- ── audit_logs ────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS audit_logs (
    log_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT       NULL,
    action     VARCHAR(200) NOT NULL,
    timestamp  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(50),
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);
