document.addEventListener('DOMContentLoaded', () => {
    const userList = document.getElementById('userList');

    function displayUsers(users) {
        console.log('Displaying users:', users); // Debugging lin
        userList.innerHTML = '';
        users.forEach(user => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.affiliation}</td>
                <td>${user.bio}</td>
                <td>${user.yearsOfExperience}</td>
                <td>${user.role}</td>
            `;
            userList.appendChild(tr);
        });
    }

    const usersData = JSON.parse(window.localStorage.getItem('usersData'));
    if (usersData) {
        displayUsers(usersData);
    } else {
        console.error('No user data found in localStorage');
    }
});
