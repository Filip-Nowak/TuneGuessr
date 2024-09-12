import React from "react";

export default function RoomInfo({ room = {} }) {
  return (
    <div style={{ width: "100%", border: "5px black dashed" }}>
      RoomInfo
      <div>
        {Object.keys(room).length === 0 ? (
          "no room"
        ) : (
          <div>
            {Object.keys(room).map((key, index) => {
              if (key === "players") {
                return (
                  <div key={index}>
                    players:
                    {room[key].map((player, index) => {
                      return (
                        <div key={index}>
                          {Object.keys(player).map((key, index) => {
                            return (
                              <div key={index}>
                                {key}: {player[key]}
                              </div>
                            );
                          })}
                        </div>
                      );
                    })}
                  </div>
                );
              }
              return (
                <div key={index}>
                  {key}: {room[key]}
                </div>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
}
