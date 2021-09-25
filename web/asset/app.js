
    import { initializeApp } from "https://www.gstatic.com/firebasejs/9.0.2/firebase-app.js";
    import { getAnalytics } from "https://www.gstatic.com/firebasejs/9.0.2/firebase-analytics.js";
    import { getDatabase , ref, set ,onValue, onChildAdded, onChildChanged, onChildRemoved  } from "https://www.gstatic.com/firebasejs/9.0.2/firebase-database.js";

    const firebaseConfig = {
      apiKey: "AIzaSyDaoR-5KLhs-4AxCxPKovjv7gOwckM9vEc",
      authDomain: "better-life-f222b.firebaseapp.com",
      databaseURL: "https://better-life-f222b-default-rtdb.firebaseio.com",
      projectId: "better-life-f222b",
      storageBucket: "better-life-f222b.appspot.com",
      messagingSenderId: "511609700498",
      appId: "1:511609700498:web:8255e68d6c84cbd478264b",
      measurementId: "G-7Y1W4W5N2B"
    };
  
    const app = initializeApp(firebaseConfig);
    const analytics = getAnalytics(app);
    const database = getDatabase(app);

    // starts

    var globalPostsList = []


    // function writeUserData(userId, name, email, imageUrl) {
    //   const db = getDatabase();
    //   set(ref(db, 'posts/' + userId), {
    //     username: name,
    //     email: email,
    //     profile_picture : imageUrl
    //   });
    // }

    window.addEventListener("load" , ()=>{
      readData()
      var refreshBtn = document.getElementById("btnrefresh");
      refreshBtn.onclick = refreshPosts
      refreshBtn.style.display = "none"

    })

    async function readData(){
      const db = getDatabase();
      const starCountRef = ref(db, 'posts/' );
      onValue(starCountRef, (snapshot) => {
        console.log("inside the firebase thingy")
        document.getElementById("posts").innerHTML=""
        var postsList = []
        snapshot.forEach((childSnapshot)=>{
          const postData = childSnapshot.val();
          var userid = postData.userId
          console.log(userid)
          const userRef = ref(db, `users/` + userid);
          var userData
          onValue(userRef, (usersnapshot) =>{
            userData = usersnapshot.val()
            postsList.push(createPostElement(postData , userData)) 
            globalPostsList = postsList
            displayPostList(postsList)
          },{
            onlyOnce: true
          })
          // console.log(postsList)
        }) 
      },{
        onlyOnce: true
      });

      // storing changes in a new array

      const postRef = ref(db, 'posts/' );
      onValue(postRef , (post)=>{
        console.log("inside the firebase thingy")
        var postsList = []
        post.forEach((childSnapshot)=>{
          const postData = childSnapshot.val();
          var userid = postData.userId
          console.log(userid)
          const userRef = ref(db, `users/` + userid);
          var userData
          onValue(userRef, (usersnapshot) =>{
            userData = usersnapshot.val()
            postsList.push(createPostElement(postData , userData)) 
            globalPostsList = postsList
          })
        }) 
      })


    }


    function createPostElement(postData , userData){
      var newPost = document.createElement("post")
      newPost.innerHTML = `
        <img src="${userData.personPhoto}" alt="image">
        <div class="post_title">${postData.title}</div>
        <div class="post_user">${postData.username}</div>
        <p class="post_des">${postData.description}</p>
      `
      newPost.className = "post"
      newPost.onclick = setFunction("post", postData.username );

      var postActions = document.createElement('postactions')
      postActions.className = "post_actions"

      var likeBtn = document.createElement("likebtn");
      likeBtn.innerHTML = `
      <img src="./asset/res/like_pic.png" alt="like"></img>
      <div class="post_likes">${postData.likes.split(",").length} likes</div>
      `
      likeBtn.onclick = setFunction("like", postData)

      var commentBtn = document.createElement("commentbtn");
      commentBtn.innerHTML = `<img src="./asset/res/comment_pic.png" alt="comment">`
      commentBtn.onclick = setFunction("comment", postData.username)

      var shareBtn = document.createElement("sharebtn");
      shareBtn.innerHTML = `<img src="./asset/res/share_pic.png" alt="share">`
      shareBtn.onclick = setFunction("share", postData.username)  

      postActions.appendChild(likeBtn)
      postActions.appendChild(commentBtn)
      postActions.appendChild(shareBtn)
      newPost.appendChild(postActions)     

      return newPost
    }

    function displayPostList(postsList){
      document.getElementById("posts").innerHTML = ""
      postsList.forEach( (newPost)=>{
        document.getElementById("posts").appendChild(newPost);
      })
    }

    function setFunction(type , data){
      if(type == "like"){
        return function onLike(){
          alert(data + " liked")
        }
      }
      else if(type == "comment"){
        return function onComment(){
          alert(data + " comment")
        }
      }
      else if(type == "share"){
        return function onShare(){
          alert(data + " share")
        }
      }
      else if(type == "post"){
        return function onPost(){
          // alert(data + " post")
        }
      }
      
    }


    function refreshPosts(){
      displayPostList(globalPostsList)
    }