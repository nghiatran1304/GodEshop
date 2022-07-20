let btnCreateRoom = document.getElementById('create__room__btn')
let url = 'http://localhost:8080/rest/getUserInfomation/'
let username = document.getElementById('adminUsername').innerText

btnCreateRoom.addEventListener('click', () => {
	getUserInfo(url, username).then((data) => {
		let roomId = data.id
		let room_name = data.account.username + data.id
		let display_name = data.fullname
		let avatar = `/upload/UserImages/` + data.photo
		let host = true
		createStreamingRoom(roomId, room_name, display_name, avatar, host)
	})
})							 			

async function getUserInfo(url, username) {
	const response = await fetch(url + username)
	const data = await response.json()
	return data;
}			

function createStreamingRoom(roomId, room_name, display_name, avatar, host) {
	sessionStorage.setItem('room_name', room_name)
	sessionStorage.setItem('avatar', avatar)
	sessionStorage.setItem('display_name', display_name)
	sessionStorage.setItem('host', host)
	window.location = `room/${roomId}`
}