document.addEventListener('DOMContentLoaded', () => {
    const token = window.localStorage.getItem("token");

    const getAllUsersBtn = document.getElementById('getAllUsers');
    const getAllStudentsBtn = document.getElementById('getAllStudents');
    const getAllInstructorsBtn = document.getElementById('getAllInstructors');
    const courseManagementBtn = document.getElementById('courseManagement');
    const  courseRequestsBtn=document.getElementById('courserequests')

    getAllUsersBtn.addEventListener('click', () => {
        fetch('http://localhost:3001/user/getall', {
            headers: {
                'Authorization':`${token}`
            }
        })
        .then(response => response.json())
        .then(data => {
            // Ensure usersData is not null or undefined before storing it
            if (data && data.users) {
                window.localStorage.setItem('usersData', JSON.stringify(data.users));
                window.location.href = 'adminDashobard.html';
            } else {
                console.error('No user data retrieved');
            }
        })
        .catch(error => console.error('Error:', error));
    });

    getAllStudentsBtn.addEventListener('click', () => {
        fetch('http://localhost:3001/user/getallStudents', {
            headers: {
                'Authorization': `${token}`
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.users) {
                window.localStorage.setItem('usersData', JSON.stringify(data.users));
                window.location.href = 'adminDashobard.html';
            } else {
                console.error('No student data retrieved');
            }
        })
        .catch(error => console.error('Error:', error));
    });

    getAllInstructorsBtn.addEventListener('click', () => {
        fetch('http://localhost:3001/user/getallInstructors', {
            headers: {
                'Authorization': `${token}`
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.users) {
                window.localStorage.setItem('usersData', JSON.stringify(data.users));
                window.location.href = 'adminDashobard.html';
            } else {
                console.error('No instructor data retrieved');
            }
        })
        .catch(error => console.error('Error:', error));
    });
     // Add event listener for course management button
     courseManagementBtn.addEventListener('click', () => {
        window.location.href = './courseMangement.html';
    }); // Add event listener for course requests button
    courseRequestsBtn.addEventListener('click', () => {
        fetch('http://localhost:7000/course/getPending', {
            headers: {
                'Authorization': `${token}`
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data && data.courses) {
                window.localStorage.setItem('pendingCourses', JSON.stringify(data.courses));
                window.location.href = 'pendingCourses.html';
            } else {
                console.error('No pending course data retrieved');
            }
        })
        .catch(error => console.error('Error:', error));
    });
});


