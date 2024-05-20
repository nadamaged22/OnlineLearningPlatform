document.addEventListener("DOMContentLoaded", function() {
    const token = window.localStorage.getItem("token");

    // Fetch pending enrollment requests from the backend
    fetchPendingEnrollments();

    function fetchPendingEnrollments() {
        // Make a GET request to your backend API using AJAX
        $.ajax({
            url: "http://localhost:8080/FinalOnlineLearning-1.0-SNAPSHOT/api/Course/pendingEnrollments",
            method: "GET",
            headers: {
                'Authorization': `${token}`
            },
            success: function(data) {
                displayPendingEnrollments(data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('Error:', errorThrown);
            }
        });
    }

    function displayPendingEnrollments(pendingEnrollments) {
        const pendingRequestsContainer = document.getElementById("pendingRequests");
        pendingRequestsContainer.innerHTML = "";

        pendingEnrollments.forEach(request => {
            const requestDiv = document.createElement("div");
            requestDiv.classList.add("request");

            const studentId = request.studentId;
            const courseId = request.courseId;
            const requestId = request._id;

            const requestInfo = document.createElement("p");
            requestInfo.textContent = `Student ID: ${studentId}, Course ID: ${courseId}`;
            requestDiv.appendChild(requestInfo);

            const acceptBtn = document.createElement("button");
            acceptBtn.classList.add("accept-btn");
            acceptBtn.textContent = "Accept";
            acceptBtn.addEventListener("click", function() {
                // Call the backend API to accept the request
                handleEnrollmentAction(requestId, "accept");
            });
            requestDiv.appendChild(acceptBtn);

            const rejectBtn = document.createElement("button");
            rejectBtn.classList.add("reject-btn");
            rejectBtn.textContent = "Reject";
            rejectBtn.addEventListener("click", function() {
                // Call the backend API to reject the request
                handleEnrollmentAction(requestId, "reject");
            });
            requestDiv.appendChild(rejectBtn);

            pendingRequestsContainer.appendChild(requestDiv);
        });
    }

    function handleEnrollmentAction(requestId, action) {
        const baseUrl = "http://localhost:8080/FinalOnlineLearning-1.0-SNAPSHOT/api/Course";
        const actionUrl = action === "accept"
            ? `${baseUrl}/acceptRequest/${requestId}`
            : `${baseUrl}/rejectRequest/${requestId}`;

        // Make an AJAX request to the appropriate URL
        $.ajax({
            url: actionUrl,
            method: "PUT",
            headers: {
                'Authorization': `${token}`
            },
            dataType: "text",  // Expect a plain text response
            success: function(response) {
                if (action === "accept") {
                    alert("Request accepted successfully");
                } else {
                    alert("Request rejected successfully");
                }
                // Refresh the list of pending requests
                fetchPendingEnrollments();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('Error:', errorThrown);
                alert('An error occurred while processing the request.');
            }
        });
    }
});
