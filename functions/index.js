const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

exports.sendTeamInviteNotification = functions.firestore.document('/users/{userEmail}/invites/{id}').onCreate((snap,context) => {
const document = snap.exists ? snap.data():null;
      if (document) {
             var message = {
               notification: {
                 title: 'You have received a team invite!',
                 body: 'Click here to view'
               },
               topic: context.params.userEmail
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
        return "doc was null or empty";
});