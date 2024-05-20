document.addEventListener("DOMContentLoaded", function() {
    const enrollBtn = document.querySelector('.enroll-btn');
    const showPastBtn = document.querySelector('.show-past-btn');
    const showCurrentBtn = document.querySelector('.show-current-btn');
    const addReviewBtn = document.querySelector('.add-review-btn');

    enrollBtn.addEventListener('click', redirectToCoursesPage);
    showPastBtn.addEventListener('click', showPastEnrollments);
    showCurrentBtn.addEventListener('click', showCurrentEnrollments);
    addReviewBtn.addEventListener('click', addReviewAndRating);

    function redirectToCoursesPage() {
        window.location.href = 'courses.html';
    }

    function showPastEnrollments() {
        // Implement logic to show past enrollments
        alert('Showing past enrollments...');
        window.location.href = 'pastEnrollements.html';
    }

    function showCurrentEnrollments() {
        // Implement logic to show current enrollments
        alert('Showing current enrollments...');
        window.location.href = 'currentEnrollements.html';
    }

    function addReviewAndRating() {
        // Implement logic to add review and rating
        alert('Adding review and rating...');
    }
});
