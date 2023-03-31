import React from 'react';
import axios from 'axios';

export default function VerifyLink() {

  
  return (
    <>
      <section className="bg-[#5b5656] min-h-screen flex items-center justify-center">
        <div className="bg-gray-100 flex rounded-2xl shadow-lg max-w-3xl p-5 items-center">
          <div className="md:w-screen px-16">
            <h2 className="font-bold text-2xl text-[#5b5656]">
              Enter the Link
            </h2>
            <div action="" className="flex flex-col gap-4">
              <input
                className="p-2 mt-8 rounded-xl border"
                type="text"
                name="verifylink"
                placeholder="Enter the Link"
                // onChange={(event) => {
                //   setTopic(event.target.value);
                // }}
              />
              <button className="bg-[#5b5656] rounded-xl text-white hover:scale-105 duration-300 py-2">
                 {/* <Link to="/chatroom">         state={{ name: name }} */}
                  Enter Chat Room
                {/* </Link> */}
              </button>
            </div>
          </div>
        </div>
      </section>
    </>
  )
}
