import axios from 'axios';


export const api = axios.create({
    baseURL: "http://localhost:8080"
})

/**
 * 
 * @returns This functions adds a new room to the db 
 */
export async function addRoom(photo, roomType, roomPrice) {
    const formData = new FormData();
    formData.append("photo", photo);
    formData.append("roomType", roomType);
    formData.append("roomPrice", roomPrice);

    const response = await api.post("/rooms/add/new-room", formData);

    if (response.status == 201) {
        return true
    } else {
        return false;
    }
}
/**
 * 
 * @returns This function fetches all the room types available
 */
export async function getRoomTypes() {
    try {
        const response = await api.get("/rooms/room/types");
        return response.data;
    } catch (error) {
        throw new Error("Error fetching room types")
    }
}

/**
 * 
 * @returns This functions gets all rooms from the database
 */
export async function getAllRooms() {
    try {
        const result = await api.get("/rooms/all-rooms");
        return result.data;
    } catch (error) {
        throw new Error("Error fetching rooms");
    }
}

/* This function deletes a room by the Id */
export async function deleteRoom(roomId) {
    try {
        const result = await api.delete(`/rooms/delete/room/${roomId}`
            //     , {
            // 	headers: getHeader()
            // }
        )
        return result.data
    } catch (error) {
        throw new Error(`Error deleting room ${error.message}`)
    }
}

/**
 * 
 * @param {*} roomId 
 * @param {*} roomData 
 * @returns this function updates a room by its id in the database
 */
export async function updateRoom(roomId, roomData) {
    const formData = new FormData();
    formData.append("roomType", roomData.roomType);
    formData.append("roomPrice", roomData.roomPrice);
    formData.append("photo", roomData.photo);

    const response = await api.put(`/rooms/update/room/${roomId}`,formData);

    return response;
}

/**
 * 
 * @param {*} roomId 
 * @returns This function gets a room by its id from the database.
 */

export async function getRoomById(roomId) {
    try {
        const result = await api.get(`/rooms/room/${roomId}`);
        return result.data;
    } catch (error) {
        throw new Error(`Error fetching room ${error.message}`);
    }
}


