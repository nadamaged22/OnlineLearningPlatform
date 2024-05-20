document.addEventListener('DOMContentLoaded', () => {
    const pendingCourses = JSON.parse(window.localStorage.getItem('pendingCourses')) || [];
    const coursesContainer = document.getElementById('coursesContainer');

    pendingCourses.forEach(course => {
        const courseItem = document.createElement('div');
        courseItem.classList.add('course-item');

        const courseDetails = document.createElement('div');
        courseDetails.classList.add('course-details');

        const courseTitle = document.createElement('h2');
        courseTitle.classList.add('course-title');
        courseTitle.textContent = course.name;

        const courseCategory = document.createElement('p');
        courseCategory.classList.add('course-category');
        courseCategory.textContent = `Category: ${course.category}`;

        const courseCapacity = document.createElement('p');
        courseCapacity.classList.add('course-capacity');
        courseCapacity.textContent = `Capacity: ${course.capacity}`;

        const courseDuration = document.createElement('p');
        courseDuration.classList.add('course-duration');
        courseDuration.textContent = `Duration: ${course.duration} hours`;

        const courseStatus = document.createElement('p');
        courseStatus.classList.add('course-status');
        courseStatus.textContent = `Status: ${course.status}`;

        const courseRating = document.createElement('p');
        courseRating.classList.add('course-rating');
        courseRating.textContent = `Rating: ${course.rating}`;

        const courseAddedBy = document.createElement('p');
        courseAddedBy.classList.add('course-addedby');
        courseAddedBy.textContent = `Added by: ${course.addedBY.email}`;

        courseDetails.appendChild(courseTitle);
        courseDetails.appendChild(courseCategory);
        courseDetails.appendChild(courseCapacity);
        courseDetails.appendChild(courseDuration);
        courseDetails.appendChild(courseStatus);
        courseDetails.appendChild(courseRating);
        courseDetails.appendChild(courseAddedBy);

        const courseActions = document.createElement('div');
        courseActions.classList.add('course-actions');

        const acceptButton = document.createElement('button');
        acceptButton.classList.add('accept');
        acceptButton.textContent = 'Accept';
        acceptButton.addEventListener('click', () => {
            handleCourseAction(course._id, 'Accepted');
        });

        const rejectButton = document.createElement('button');
        rejectButton.classList.add('reject');
        rejectButton.textContent = 'Reject';
        rejectButton.addEventListener('click', () => {
            handleCourseAction(course._id, 'Rejected');
        });

        courseActions.appendChild(acceptButton);
        courseActions.appendChild(rejectButton);

        courseItem.appendChild(courseDetails);
        courseItem.appendChild(courseActions);

        coursesContainer.appendChild(courseItem);
    });

    function handleCourseAction(courseId, status) {
        fetch(`http://localhost:7000/course/updateStatus/${courseId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `${window.localStorage.getItem('token')}`
            },
            body: JSON.stringify({ status })
        })
        .then(response => response.json())
        .then(data => {
            if (data.message === "Course Status Update SUCCESS!") {
                alert(`Course ${status.toLowerCase()} successfully`);
                window.location.reload(); // Refresh the page to update the list of courses
            } else {
                console.error('Error:', data.message);
            }
        })
        .catch(error => console.error('Error:', error));
    }
});
