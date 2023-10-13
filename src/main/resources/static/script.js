var stompClient = null;

$(document).ready(function () {
    console.log('Index page is ready');
    connect();
});

function connect() {
    const socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        updateNotificationDisplay();
        stompClient.subscribe('/topic/global-notifications', async function (message) {
            await updateNotificationDisplay();
        });
    });
}

async function updateNotificationDisplay() {
    const responseString = await fetch('http://localhost:8080/steps/leaderboard/daily?limit=10');
    const response = await responseString.json();
    const ranks = response.data || [];
    let myHtml = "";

    $.each( ranks, function( i, item ) {
        let top = `TOP ${i+1}: ${item.name} - ${item.email} - ${item.steps} bước `;
        myHtml += "<li>" + top + "</li>";
    });
    $( "#ranking" ).html( myHtml );
}
