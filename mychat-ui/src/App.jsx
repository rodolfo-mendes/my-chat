import { useEffect, useState } from 'react';
import MessageList from './components/MessageList';
import MessageInput from './components/MessageInput';
import ChatList from './components/ChatList';
import { listChats, createChat, sendMessage, getChat } from './services/chatApi'
import './App.css';

function App() {
  const [chats, setChats] = useState([]);
  const [selectedChatId, setSelectedChatId] = useState([]);
  const [messages, setMessages] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  // Fetch chat list on component mount
  useEffect(() => {
    const fetchChats = async () => {
      try {
        const chatList = await listChats();
        setChats(chatList)
        if (chatList.length > 0) {
          setSelectedChatId(chatList[0].id);
        }
      } catch (err) {
        setError('Failed to load chats');
        console.error(err);
      }
    }

    fetchChats();
  }, []);

  // Fetch messages when selected chat changes
  useEffect(() => {
    if (!selectedChatId) return;

    const fetchChatMessages = async () => {
      try {
        setIsLoading(true);
        const chat = await getChat(selectedChatId);
        setMessages(chat.messages);
        setError(null);
      } catch (err) {
        setError('Failed to load chat');
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchChatMessages();
  }, [selectedChatId]);

  const handleSelectChat = (chatId) => {
    setSelectedChatId(chatId);
  }

  const handleNewChat = () => {
    setMessages([]);
    setSelectedChatId(null);
  }


  const handleSendMessage = async (text) => {
    setIsLoading(true);
    setError(null);

    try {
      // First message: create new chat
      if (!selectedChatId) {
        const response = await createChat(text);
        setSelectedChatId(response.id);
        setMessages(response.messages);

        // Refresh chat list
        const chatList = await listChats();
        setChats(chatList);
      } else {
        // Send message to existing chat
        const response = await sendMessage(selectedChatId, text);
        setMessages([...messages, response])
      }
    } catch (err) {
      setError(err.message);
      console.error('Error: ', err);
    } finally {
      setIsLoading(false);
    }
  }

  const currentChatTitle = chats.find(c => c.id === selectedChatId)?.title || 'New Chat';

  return (
    <div className="app">
      <div className="app-container">
        <ChatList
          chats={chats}
          selectedChatId={selectedChatId}
          onSelectChat={handleSelectChat}
          onNewChat={handleNewChat}
          isLoading={isLoading}
        />
        <div className="chat-area">
          <div className="chat-header">
            <h1>{currentChatTitle}</h1>
          </div>
          {error && <div className="error">[error]</div>}
          <MessageList messages={messages} />
          <MessageInput onSendMessage={handleSendMessage} isLoading={isLoading}/>
        </div>
      </div>
    </div>
  );
}

export default App;