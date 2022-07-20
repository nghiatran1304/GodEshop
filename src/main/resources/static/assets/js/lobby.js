let appID = '9026d01943724f1c8178e41aa938e367'
let token = null
let uid = String(Math.floor(Math.random() * 10000))
let roomData = {}

let initiate = async () => {
    let rtmClient = await AgoraRTM.createInstance(appID)
    await rtmClient.login({uid, token})

    const lobbyChannel = await rtmClient.createChannel('lobby')
    await lobbyChannel.join()

    rtmClient.on('MessageFromPeer', async (message, peerId) => {
        let messageData = JSON.parse(message.text)
        let count = await rtmClient.getChannelMemberCount([messageData.room])
        roomData[messageData.room] = {'members':count}

        let rooms = document.getElementById('room__container')
        let room = document.getElementById(`room__${messageData.room}`)

        if (room === null) {
            room = await buildRoom(count, messageData.room)
            rooms.insertAdjacentHTML('beforeend', room)
        }

    })

    let buildRoom = async (count, room_id) => {
        let attributes = await rtmClient.getChannelAttributesByKeys(room_id, ['room_name', 'host', 'host_image'])
        let roomName = attributes.room_name.value
        let host = attributes.host.value
        
        let host_image = attributes.host_image.value
        let roomItem = `<div class="room__item" id="room__${room_id}">
        					<p id="streaming" style="color:red; z-index: 9999999; position: absolute;"><img style="width: 60px; height:40px; color: red;" src="/assets/images/streaming.png"></p>
                            <img class="room__photo" src="./assets/images/stream-thumbnail.png" alt="">
                            <div class="room__content">
                                <p class="room__meta">
                                	<h4 class="room_title">${roomName}</h4>
                                </p>
                                
                                <div class="room__box">
                                    <div class="room_author">
                                        <img src="${host_image}" class="avatar__md" alt="">
                                        <strong class="message__author">${host}</strong>
                                    </div>
                                    
                                    <a href="/room/${room_id}" class="join__btn">Join Now</a>
                                    
                                </div>
                                <span>${count[room_id]} Watching</span>
                            </div>
                        </div>`
        return roomItem
    }

    let checkJoined = async () => {
        for (let room_id in roomData) {
            let count = await rtmClient.getChannelMemberCount([room_id])
            if (count[room_id] < 1) {
                document.getElementById(`room__${room_id}`).remove()
                delete roomData[room_id]
            } else {
                let newRoom;
                let rooms = document.getElementById('room__container')
                newRoom = await buildRoom(count, room_id)
                document.getElementById(`room__${room_id}`).innerHTML = newRoom
            }
        }
    }

    



    let interval = setInterval(() => {
        checkJoined()   
    }, 3000)
    return () => {
        clearInterval(interval)
    }
}

initiate()

