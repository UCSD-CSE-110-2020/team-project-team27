const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const admin = require('firebase-admin');
admin.initializeApp();

exports.addUserRoute = functions.firestore
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
                     });

// run below in terminal to deploy function to firestore
// firebase deploy --only functions