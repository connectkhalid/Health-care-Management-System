
import React, { useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import { ZegoUIKitPrebuilt } from "@zegocloud/zego-uikit-prebuilt";
import { loadLocalStorage } from "../../utils/persistLocalStorage";

const Telemedicine = () => {
    const token = loadLocalStorage("token");
    var user = loadLocalStorage("user");

  const { roomId } = useParams();
  const roomRef = useRef(null);
  let zp = useRef(null); // Use useRef to maintain the zp instance

  useEffect(() => {
    const appId = "1071066020";
    const serverSecret = "bb665b0559edeb55a03bd09f62761f6a";
    const userName = user?.name;

    const kitToken = ZegoUIKitPrebuilt.generateKitTokenForTest(
      appId,
      serverSecret,
      roomId,
      Date.now().toString(),
      userName,
    );

    zp.current = ZegoUIKitPrebuilt.create(kitToken);

    zp.current.joinRoom({
      container: roomRef.current,
      scenario: { mode: ZegoUIKitPrebuilt.VideoConference },
    });

    return () => {
      if (zp.current && zp.current.leaveRoom) {
        zp.current.leaveRoom();
      }
    };
  }, [roomId]);

  return (
    <div className="room-page">
      <div ref={roomRef} style={{ width: "100%", height: "100vh" }} />
    </div>
  );
};

export default Telemedicine;