import { useState } from 'react';

function MessageInput({ onSendMessage, isLoading }) {
    const [inputValue, setInputValue] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault(); // prevent page reload

        if (inputValue.trim() === '') return; // Don't send empty messages

        onSendMessage(inputValue); // Tell parent the message
        setInputValue('');
    };

    return (
        <form onSubmit={handleSubmit} className="message-input">
            <textarea
                type="text"
                value={inputValue}
                onChange={(e) => setInputValue(e.target.value)}
                placeholder="Type your message ..."
                disabled={isLoading}
                rows="4"
            />
            <button type="submit" disabled={isLoading}>
                {isLoading ? 'Sending...' : 'Send'}
            </button>
        </form>
    );
}

export default MessageInput;