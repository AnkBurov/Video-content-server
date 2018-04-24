# Video content server

## Encoding commands
ffmpeg -i star_trails.mp4 -s hd1080 -c:v libx264 -crf 23 -strict -2 star_trails_1080p.mp4


MP4Box -dash 8000 -frag 8000 -rap -url-template -segment-name '$RepresentationID$/segment_' star_trails_1080p.mp4#video:id=1080p star_trails_1080p.mp4#audio:id=audio


MP4Box -dash 8000 -frag 8000 -rap -segment-name '$RepresentationID$/segment_' with_sound_1080p.mp4#video:id=1080p with_sound_720p.mp4#video:id=720p with_sound_1080p.mp4#audio:id=audio