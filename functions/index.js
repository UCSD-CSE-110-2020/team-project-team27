const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const admin = require('firebase-admin');
admin.initializeApp();

/*exports.addUserRoute = functions.firestore
                     .document('users/{userEmail}/routes/{routeId}')
                     .onCreate((snap, context) => {
                       if (snap) {
                         return snap.ref.update({
                                     timestamp: admin.firestore.FieldValue.serverTimestamp()
                                 });
                       }

                       return "snap was null or empty";
                     });
exports.addUserTeammates = functions.firestore
                     .document('users/{userEmail}/teammates/{teammateId}')
                     .onCreate((snap, context) => {
                       if (snap) {
                         return snap.ref.update({
                                     timestamp: admin.firestore.FieldValue.serverTimestamp()
                                 });
                       }

                       return "snap was null or empty";
                     });*/


//exports.sendTeamInviteNotification = functions.firestore.document('/users/{userEmail}/{invites}/')
//                                         .onCreate(async (change, context) =>  {
//         const inviteeUid = context.params.userEmail;
//
//         console.log('We have a new invitee:', inviteeUid);
//
//         // Get the list of device notification tokens.
//         const getDeviceTokensPromise = admin.database()
//          .ref(`/users/${userEmail}/invites/notificationTokens`).once('value');
//
//         // The snapshot to the user's tokens.
//         let tokensSnapshot;
//
//         // The array containing all the user's tokens.
//         let tokens;
//
//         const results = await Promise.all([getDeviceTokensPromise, getFollowerProfilePromise]);
//         tokensSnapshot = results[0];
//         const follower = results[1];
//
//         // Check if there are any device tokens.
//         if (!tokensSnapshot.hasChildren()) {
//           return console.log('There are no notification tokens to send to.');
//         }
//         console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');
//         console.log('Fetched follower profile', follower);
//
//         // Notification details.
//         const payload = {
//           notification: {
//               title: 'You have been invited to a team!',
//               body: `${follower.userEmail} is requesting to form a team.`,
//           }
//         };
//
//         // Listing all tokens as an array.
//         tokens = Object.keys(tokensSnapshot.val());
//         // Send notifications to all tokens.
//         const response = await admin.messaging().sendToDevice(tokens, payload);
//         // For each message check if there was an error.
//         const tokensToRemove = [];
//         response.results.forEach((result, index) => {
//           const error = result.error;
//           if (error) {
//               console.error('Failure sending notification to', tokens[index], error);
//               // Cleanup the tokens who are not registered anymore.
//               if (error.code === 'messaging/invalid-registration-token' ||
//                   error.code === 'messaging/registration-token-not-registered') {
//                 tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
//               }
//           }
//           }
//         });
//         return Promise.all(tokensToRemove);
//         };
//
//});

exports.sendTeamInviteNotification = functions.firestore.document('/users/{userEmail}/{invites}').onCreate((snap,context) => {
        const document = snap.exists ? snap.data():null;
      if (document) {
             var message = {
               notification: {
                 title: document.from + ' sent you a message',
                 body: document.text
               },
               topic: context.params.invites
             };

             return admin.messaging().send(message)
               .then((response) => {
                 // Response is a message ID string.
                 console.log('Successfully sent message:', response);
                 return response;
               })
               .catch((error) => {
                 console.log('Error sending message:', error);
                 return error;
               });
           }


        Log.d("doc was null or empty");
        return "doc was null or empty"
}




// run below in terminal to deploy function to firestore
// firebase deploy --only functions