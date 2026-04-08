-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2025 at 03:46 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ccsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `health_logs`
--

CREATE TABLE `health_logs` (
  `log_id` int(11) NOT NULL,
  `resident_id` int(11) DEFAULT NULL,
  `staff_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `notes` text DEFAULT NULL,
  `treatment_given` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `residents`
--

CREATE TABLE `residents` (
  `resident_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `IC_number` varchar(20) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` enum('male','female') NOT NULL,
  `medical_history` text DEFAULT NULL,
  `allergies` text DEFAULT NULL,
  `care_plan` text DEFAULT NULL,
  `assigned_staff_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `residents`
--

INSERT INTO `residents` (`resident_id`, `name`, `IC_number`, `age`, `gender`, `medical_history`, `allergies`, `care_plan`, `assigned_staff_id`) VALUES
(1, 'Lim Ah Kow', '520311081245', 73, 'male', 'Type 2 Diabetes', 'None', 'Daily Insulin Injection, Diabetic Diet Monitoring', NULL),
(2, 'Tan Siew Lan', '540712109981', 71, 'female', 'Hypertension', 'Shellfish', 'Monitor BP, low-sodium diet, monthly check-up', NULL),
(3, 'Wong Chee Seng', '500928142234', 74, 'male', 'Chronic Kidney Desease', 'NSAIDs', 'Daily 3x week, fluid intake control', NULL),
(4, 'Shen Xiao Ting', '531115011123', 72, 'female', 'Osteoporosis', 'none', 'Calcium supplements, fall prevention program', NULL),
(5, 'Zhang Hao', '490905019987', 75, 'male', 'Glaucoma', 'none', 'Daily eye drop, annual vision screening', NULL),
(6, 'Lee Siew Kuan', '530607010209', 72, 'female', 'Hearts  Disease', 'Penicilin', 'Beta Blockers, Cardiac rehab twice weekly', NULL),
(7, 'Mark Yi En Tuan', '510102010808', 74, 'male', 'COPD', 'Dust mites', 'Nebuziler therapy, pulmonary rehab', NULL),
(8, 'Chua Bee Lian', '550918091155', 69, 'female', 'Rheumatoid Arthritis', 'Gluten', 'Pain management, physiotherapy', NULL),
(9, 'Cheng Xiao', '520228010023', 73, 'female', 'Prostate Enlargement', 'none', 'Medication, yearly urology review', NULL),
(10, 'Koh Siew Eng', '510206010228', 74, 'female', 'Depression', 'none', 'weekly theraphy sessions, medication monitoring', NULL),
(11, 'Jackson Wang', '501012010008', 75, 'male', 'Stroke(past history)', 'none', 'Mobility aid support, speech therapy', NULL),
(12, 'Liew Ah Moy', '480618010208', 76, 'female', 'Dementia', 'none', 'Supervised daily care, memory exercises', NULL),
(13, 'Goh Kim Seng', '530712010880', 72, 'male', 'High Cholestrol', 'None', 'Statin therapy, deitary caunselling', NULL),
(15, 'Sim Ah Kow', '49030701028', 76, 'male', 'Hearing loss', 'none', 'Hearing aid use, routine ENT checkups', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `shifts`
--

CREATE TABLE `shifts` (
  `shift_id` int(11) NOT NULL,
  `staff_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `shift_type` enum('morning','evening','night') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shifts`
--

INSERT INTO `shifts` (`shift_id`, `staff_id`, `date`, `start_time`, `end_time`, `shift_type`) VALUES
(3, 4, '2025-06-27', '22:00:00', '06:00:00', 'morning'),
(5, 4, '2025-06-27', '22:00:00', '06:00:00', 'morning');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `IC_number` varchar(20) NOT NULL,
  `position` enum('caregiver','nurse','doctor') NOT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `address` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `user_id`, `name`, `IC_number`, `position`, `contact_number`, `address`) VALUES
(2, 8, 'Nang Ainaa Syaza Binti Jamalulail', '040618010208', 'nurse', '01128810837', 'No. 1346 Jalan Hang Tuah'),
(3, 9, 'Nurul Asyiqin Binti Abdul', '040424120989', 'caregiver', '01234567897', 'No. 4379 Jalan Hang Tuah Taman Perdana Kuala Lumpur'),
(4, 10, 'Nur Alyaa\' Syafiah Binti Aliminn', '040829132898', 'doctor', '019287463788', 'No 4 TL 12 Jalan Pt Samion Parit Raja Batu Pahat Johor'),
(5, 11, 'Nur Nadhihah Binti Abdul Aziz', '040114038478', 'nurse', '0187283645', 'No 10 Jalan Taman Parkland Kluang Baru Johor');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('admin','staff','it') NOT NULL,
  `status` enum('active','inactive') DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `role`, `status`) VALUES
(1, 'asmah', 'asmah123', 'admin', 'active'),
(2, 'asyiqin', 'asyiqin123', 'staff', 'active'),
(3, 'alyaa', 'alyaa123', 'staff', 'active'),
(8, 'nang', '040618010208', 'staff', 'active'),
(9, 'syiqin', '040424120989', 'staff', 'active'),
(10, 'alyo', '040829132898', 'staff', 'active'),
(11, 'nad ', '040114038478', 'staff', 'active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `health_logs`
--
ALTER TABLE `health_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `resident_id` (`resident_id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- Indexes for table `residents`
--
ALTER TABLE `residents`
  ADD PRIMARY KEY (`resident_id`),
  ADD UNIQUE KEY `IC_number` (`IC_number`),
  ADD KEY `assigned_staff_id` (`assigned_staff_id`);

--
-- Indexes for table `shifts`
--
ALTER TABLE `shifts`
  ADD PRIMARY KEY (`shift_id`),
  ADD KEY `staff_id` (`staff_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staff_id`),
  ADD UNIQUE KEY `IC_number` (`IC_number`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `health_logs`
--
ALTER TABLE `health_logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `residents`
--
ALTER TABLE `residents`
  MODIFY `resident_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `shifts`
--
ALTER TABLE `shifts`
  MODIFY `shift_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staff_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `health_logs`
--
ALTER TABLE `health_logs`
  ADD CONSTRAINT `health_logs_ibfk_1` FOREIGN KEY (`resident_id`) REFERENCES `residents` (`resident_id`),
  ADD CONSTRAINT `health_logs_ibfk_2` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`);

--
-- Constraints for table `residents`
--
ALTER TABLE `residents`
  ADD CONSTRAINT `residents_ibfk_1` FOREIGN KEY (`assigned_staff_id`) REFERENCES `staff` (`staff_id`);

--
-- Constraints for table `shifts`
--
ALTER TABLE `shifts`
  ADD CONSTRAINT `shifts_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
