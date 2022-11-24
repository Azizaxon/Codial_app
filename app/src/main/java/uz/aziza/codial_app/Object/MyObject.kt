package uz.aziza.codial_app.Object

import uz.aziza.codial_app.models.Courses
import uz.aziza.codial_app.models.Groups
import uz.aziza.codial_app.models.Mentors
import uz.aziza.codial_app.models.Students

object MyObject {
    var tvAllCoursesName = ""
    var booleanAddCourses = true
    var courses = Courses()
    var mentors = Mentors()
    var groups = Groups()
    var students = Students()
    var navigationID = 0
    var editStudent = false
}