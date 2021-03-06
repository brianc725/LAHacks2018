const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
 
var database = firebase.database();

exports.addParty = functions.https.onRequest((req, res) =>{
	const original = req.query.text;

	return admin.database().ref('/partyid').push({id: original})(.then((snapshot) =>{
		return res.redirect(303, snapshot.ref);
	});
});

exports.createPhotos = functions.database.ref().onWrite((event) =>{
	const original = event.data.val();

})