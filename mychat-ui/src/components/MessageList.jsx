function MessageList({messages}) {
    return (
        <div className="message-list">
            {messages.map((message) => (
                <div key={message.id} className="message">
                    <p>{message.text}</p>
                    <small>{new Date(message.receivedAt).toLocaleTimeString()}</small>
                </div>
            ))}
        </div>
    );
}

export default MessageList;