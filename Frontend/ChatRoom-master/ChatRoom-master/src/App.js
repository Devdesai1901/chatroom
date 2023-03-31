import UserLogin from "./pages/UserLogin.js";
import UserRegister from "./pages/UserRegister.js";
import OtpScreen from "./pages/OtpScreen.js";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainDashboard from "./pages/MainDashboard.js";
import SelectTopic from "./pages/SelectTopic.js";
import ChatRoom from "./pages/ChatRoom.js";
import AddParticipant from "./pages/AddParticipant.js";
import GenerateLink from "./pages/GenerateLink.js";
import VerifyLink from "./pages/VerifyLink.js";
// import { ToastContainer } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
    <div className="App">
      {/* <ToastContainer/> */}
      <BrowserRouter>
        <Routes>
          <Route path="/">
            <Route index element={<MainDashboard />} />
            <Route exact path="login" element={<UserLogin />} />
            <Route exact path="signup" element={<UserRegister />} />
            <Route exact path="otp" element={<OtpScreen />} />
            <Route exact path="topic" element={<SelectTopic />} />
            <Route exact path="chatRoom" element={<ChatRoom />} />
            <Route exact path="addparticipant" element={<AddParticipant />} />
            <Route exact path="generatelink" element={<GenerateLink />} />
            <Route exact path="Verifylink" element={<VerifyLink />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
