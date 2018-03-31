<<<<<<< HEAD
// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
=======
>>>>>>> 309cef8f54bf305b5b04314ddc64ac69a98ff0f5
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

<<<<<<< HEAD
/*
// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
exports.addMessage = functions.https.onRequest((req, res) => {
	// Grab the text parameter.
	const original = req.query.text;
	// Push the new message into the Realtime Database using the Firebase Admin SDK.
	return admin.database().ref('/messages').push({original: original}).then((snapshot) => {
		// Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
		return res.redirect(303, snapshot.ref);
	    });
    });
*/
/*
// Listens for new messages added to /messages/:pushId/original and creates an
// uppercase version of the message to /messages/:pushId/uppercase
exports.makeUppercase = functions.database.ref('/messages/{pushId}/original').onWrite((event) => {
	// Grab the current value of what was written to the Realtime Database.
	const original = event.data.val();
	console.log('Uppercasing', event.params.pushId, original);
	const uppercase = original.toUpperCase();
	// You must return a Promise when performing asynchronous tasks inside a Functions such as
	// writing to the Firebase Realtime Database.
	// Setting an "uppercase" sibling in the Realtime Database returns a Promise.
	return event.data.ref.parent.child('uppercase').set(uppercase);
    });
*/

data = "asdf";

// Saves a message to the Firebase Realtime Database but sanitizes the text by removing swearwords.
exports.addMessage = functions.https.onCall((data, context) => {
	// ...
    });
=======
exports.addParty = functions.https.onRequest((req, res) =>{
	const original = req.query.text;

	return admin.database().ref('/partyid').push({id: original})(.then((snapshot) =>{
		return res.redirect(303, snapshot.ref);
	});
});
>>>>>>> 309cef8f54bf305b5b04314ddc64ac69a98ff0f5
