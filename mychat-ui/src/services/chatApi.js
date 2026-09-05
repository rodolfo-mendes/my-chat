const API_BASE_URL = 'http://localhost:8080'

function validateChatId(chatId) {
  if (chatId == null) throw new Error('chatId is required');
  if (!Number.isInteger(chatId)) throw new Error('chatId must be an integer');
  if (chatId <= 0) throw new Error('chatId must be a positive integer');
}

export const listChats = async () => {
    const response = await fetch(`${API_BASE_URL}/chats`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });

    if (!response.ok) throw new Error('Failed to list chats');
    return response.json();
}

export const getChat = async (chatId) => {
    validateChatId(chatId);

    const response = await fetch(`${API_BASE_URL}/chats/${chatId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    });

    if (!response.ok) throw new Error('Failed to get chat');

    return response.json();
}

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
    validateChatId(chatId);
    
    const response = await fetch(`${API_BASE_URL}/chats/${chatId}/messages`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text: messageText })
    });

    if (!response.ok) throw new Error('Failed to send message');

    return response.json();
};