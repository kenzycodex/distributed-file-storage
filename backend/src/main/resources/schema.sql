-- Initialize Database Schema
-- This file will be automatically executed by Spring Boot during startup

-- Ensure proper database creation and character set
CREATE DATABASE IF NOT EXISTS cloudstore_db
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

USE cloudstore_db;

-- Enable UUID support
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Additional database-level configurations
SET GLOBAL time_zone = '+00:00';