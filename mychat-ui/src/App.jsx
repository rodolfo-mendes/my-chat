import { useState } from 'react';
import MessageList from './components/MessageList';
import MessageInput from './components/MessageInput';
import { createChat, sendMessage} from './services/chatApi'
import './App.css';

function App() {
  const [messages, setMessages] = useState([]);
  const [chatId, setChatId] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);


  const handleSendMessage = async (text) => {
    setIsLoading(true);
    setError(null);

    try {
      if (!chatId) {
        const response = await createChat(text);
        setChatId(response.id);
        setMessages(response.messages);
      } else {
        const response = await sendMessage(chatId, text);
        setMessages([...messages, response])
      }
    } catch (err) {
      setError(err.message);
      console.error('Error: ', err);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className="app">
      <h1>Chatbot</h1>
      {error && <div className="error">[error]</div>}
      <MessageList messages={messages} />
      <MessageInput onSendMessage={handleSendMessage} isLoading={isLoading}/>
    </div>
  );
}

export default App;