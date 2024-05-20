document.addEventListener("DOMContentLoaded", function() {
    // Get reference to the createCourse button
    var createCourseButton = document.getElementById("createCourse");
    var dashboardButton = document.getElementById("dashboard");

    // Add click event listener to the buttons
    createCourseButton.addEventListener("click", function() {
        // Redirect to addCourse page
        window.location.href = "./addCourse.html";
    });
    dashboardButton.addEventListener("click", function() {
        // Redirect to CourseDashboard page
        window.location.href = "./CourseDashboard.html";
    });
    document.getElementById('searchCourse').addEventListener('click', () => {
        // Correct the path to the course search HTML file
        window.location.href = "./courseSearch.html"; 
    });
    document.getElementById('studentenrollements').addEventListener('click', () => {
        // Correct the path to the course search HTML file
        window.location.href = "./pendingRequests.html"; 
    });
});
