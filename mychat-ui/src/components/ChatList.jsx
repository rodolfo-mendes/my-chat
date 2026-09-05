import { useState } from 'react';

function ChatList ({chats, selectedChatId, onSelectChat, onNewChat, isLoading}) {
    return (
        <div className="chat-list">
            <div className="chat-list-header">
                <h2>Chats</h2>
            </div>

            <button
                className="new-chat-btn"
                onClick={onNewChat}
                disabled={isLoading}
            >
                + New Chat 
            </button>

            <div className="chat-list-items">
                {chats.map((chat) => (
                    <div
                        key={chat.id}
                        className={`chat-item ${selectedChatId == chat.id} ? 'active' : ''`}
                        onClick={() => onSelectChat(chat.id)}
                    >
                        <div className="chat-item-title">{chat.title}</div>
                        <div className="chat-item-date">{new Date(chat.date).toLocaleDateString()}</div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default ChatList;