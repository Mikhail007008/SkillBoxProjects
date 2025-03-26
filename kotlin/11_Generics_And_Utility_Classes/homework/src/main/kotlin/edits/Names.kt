package org.example.edits

enum class Names {
    John, Michael, David, James, Robert, William, Richard, Charles, Joseph, Thomas,
    Christopher, Daniel, Paul, Mark, George, Steven, Edward, Brian, Kevin, Ronald,
    Jason, Jeffrey, Gary, Ryan, Eric, Stephen, Jacob, Scott, Brandon, Frank;

    companion object {
        fun getRandomName(): String {
            return entries.toTypedArray().random().name
        }
    }
}