const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

exports.sendTeamInviteNotification = functions.firestore.document('/users/{userEmail}/invites/{id}').onCreate((snap,context) => {
const document = snap.exists ? snap.data():null;
      if (document) {
             var message = {
               notification: {
                 //document.FIELD (field from firebase)
                 title: document.Name + ' sent you a team invite!',
                 body: 'Click here to view'
               },
               topic: context.params.userEmail
             };

             console.log(document);

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

        return "doc was null or empty";
});

exports.sendTeamAcceptNotification = functions.firestore.document('/users/{userEmail}/team/{id}').onUpdate((snap,context) => {
//Need to use .after for data from after
const document = snap.after.exists ? snap.after.data():null;
      if (document) {
             var message = {
               notification: {
                 //document.FIELD (field from firebase)
                 title: document.Name + ' accepted your invite!',
                 body: 'Click here to view'
               },
               topic: context.params.userEmail
             };

             console.log(document);

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

        console.log('Error sending message:', error);
        return "doc was null or empty";
});

exports.sendTeamRejectNotification = functions.firestore.document('/users/{userEmail}/team/{id}').onDelete((snap,context) => {
const document = snap.exists ? snap.data():null;
      if (document) {
             var message = {
               notification: {
                 //document.FIELD (field from firebase)
                 title: document.Name + ' rejected your invite!',
                 body: 'Click here to view'
               },
               topic: context.params.userEmail
             };

             console.log(document);

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


exports.sendTeamProposeRouteNotification = functions.firestore.document('/users/{userEmail}/newProposedRoutes/{id}').onCreate((snap, context) => {
const document = snap.exists ? snap.data():null;
      // Print out someone scheduled
      if (document) {
             var message = {
               notification: {
                 //document.FIELD (field from firebase)
                 title: document.Owner + ' proposed a route.',
                 body: document.Name + " at " + document.Time + ', ' + document.Date
               },
               topic: context.params.userEmail
             };
      }
});

exports.sendTeamScheduleRouteNotification = functions.firestore.document('/users/{userEmail}/newProposedRoutes/{id}').onUpdate((snap,context) => {
const document = snap.after.exists ? snap.after.data():null;
      // Print out someone scheduled
      if (document.Scheduled == "true") {
             var message = {
               notification: {
                 //document.FIELD (field from firebase)
                 title: document.Owner + ' scheduled a route.",
                 body: document.Name + " at " + document.Time ", " + document.Date
               },
               topic: context.params.userEmail
             };

             console.log(document);

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
       } else if (document.Scheduled == "false") {
            //Print out someone withdrawed
            var message = {
                           notification: {
                             //document.FIELD (field from firebase)
                             title: document.Owner + ' withdrew a route.",
                             body: document.Name + " at " + document.Time ", " + document.Date
                           },
                           topic: context.params.userEmail
                         };

                         console.log(document);

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

/*
exports.sendTeamTeammateDecisionNotification = functions.firestore.document('/users/{userEmail}/proposedRoute/{id}').onUpdate((snap,context) => {
const document = snap.after.exists ? snap.after.data():null;
      // Print out someone accepted (check if doc attendee b4
      if (document.Attendees != snap.before.data().Attendees) {
            var newAttendee = document.Recent;

             var message = {
               notification: {
                 //document.FIELD (field from firebase)
                 title: newAttendee + ' accepted your route!',
                 body: document.Name + " at " + document.Time ", " + document.Date
               },
               topic: context.params.userEmail
             };

             console.log(document);

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
       } else {
            //Print out someone rejected
            var newRejected = document.Recent;

                         var message = {
                           notification: {
                             //document.FIELD (field from firebase)
                             title: newRejected + ' rejected your route!',
                             body: document.Name + " at " + document.Time ", " + document.Date
                           },
                           topic: context.params.userEmail
                         };

                         console.log(document);

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
});*/