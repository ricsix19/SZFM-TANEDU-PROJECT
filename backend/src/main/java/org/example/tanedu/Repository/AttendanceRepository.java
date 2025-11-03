package org.example.tanedu.Repository;

import org.example.tanedu.Model.Attendance;
import org.example.tanedu.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByCourse_Id(Long courseId);
    List<Attendance> findAllByCourse_IdAndSessionDate(Long courseId, LocalDate sessionDate);
    List<Attendance> findAllByStudent(User student);
}
