package com.pk.palace.model


data class Exercise (
    val id: Int,
    val title: String,
    val description: String,
    val category: NoteCategory,
    val targetGroups: List<TargetGroup>,
    val content: Content
)


//data class Image(
//    override val id: Int,
//    override val title: String,
//    override val description: String,
//    override val category: NoteCategory,
//    val url: String
//): Exercise {
//    override fun contentType() = ContentType.IMAGE
//}
//
//data class Gif(
//    override val id: Int,
//    override val title: String,
//    override val description: String,
//    override val category: NoteCategory,
//    val url: String
//): Exercise {
//    override fun contentType() = ContentType.GIF
//}