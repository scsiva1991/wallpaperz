import sched, time, urllib.request, json, pyrebase

#Config settings for firebase
config = {
  "apiKey": "AIzaSyAVQv5QuZQcTv_qruC6bfVxPcp2L4E47ug",
  "authDomain": "wallpaperz-e5a5d.firebaseapp.com",
  "databaseURL": "https://wallpaperz-e5a5d.firebaseio.com",
  "storageBucket" :"wallpaperz-e5a5d.appspot.com"
}

#Initialize firebase
firebase = pyrebase.initialize_app(config)

auth = firebase.auth()

#Signin with user already created
user = auth.sign_in_with_email_and_password('siva@gmail.com', 'test1234')
db = firebase.database()

if __name__ == '__main__':
    count = 26 #Script last downloaded page 26
    #Initialize the scheduler
    downloadSchedule = sched.scheduler(time.time, time.sleep)

    def downloadAndUpload(sc, count):
        print('count', count)
        count += 1
        print("Started downloading from unsplash...", "page ", count)
        url = "https://api.unsplash.com/photos/?page="+str(count)+"&client_id=6ff1bf6063364eee637b7b4d25f3bbe1447a0aeecf73734fb18176f953c448fd"
        res = urllib.request.urlopen(url).read()
        data = json.loads(res.decode('utf-8'))
        print("Completed downloading from unsplash...")

        for url in data:
            obj = {}
            obj["small"] = url['urls']['small']
            obj["thumb"] = url['urls']['thumb']
            obj["raw"] = url['urls']['raw']
            obj["regular"] = url['urls']['regular']
            obj["full"] = url['urls']['full']
            #Upload the firebase with image url
            db.child("images").push(obj, user['idToken'])
        print("Completed uploading to firebase...")
        print("--------------------------------------------------------------------------------------------------")
        downloadSchedule.enter(120, 1, downloadAndUpload, (sc, count))

    downloadSchedule.enter(120, 1, downloadAndUpload, (downloadSchedule, count))
    downloadSchedule.run()
