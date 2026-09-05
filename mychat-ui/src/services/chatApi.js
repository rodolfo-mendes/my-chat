const API_BASE_URL = 'http://localhost:8080'

export const createChat = async (initialMessage) => {
    const response = await fetch(`${API_BASE_URL}/chats`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            message: {text: initialMessage}
        })
    })

    if (!response.ok) throw new Error('Failed to create chat');

    return response.json();
};

export const sendMessage = async (chatId, messageText) => {
    const response = await fetch(`${API_BASE_URL}/chats/${chatId}/messages`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text: messageText })
    });

    if (!response.ok) throw new Error('Failed to send message');

    return response.json();
};