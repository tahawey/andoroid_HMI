package com.example.tahaweyplccontrol

class point {
    var name:String?=null
    var des:String?=null
    var image:Int?=null
    constructor(name:String,des:String,image: Int){
        this.name=name
        this.des=des
        this.image=image
    }

    constructor(name:String){
    this.name=name
  }
}