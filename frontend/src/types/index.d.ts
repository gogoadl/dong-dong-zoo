export interface Authentication {
  email: string;
  password: string;
}
export interface SignUp {
  username: string;
  email: string;
  password: string;
}
export interface NullUser {
  user: number;
}
export interface Song {
  songId: number | string;
  songName: string | null;
  songImageUrl: string | null;
  views: number;
  likeCount: number;
}
export interface SongDetail {
  beat: number;
  likeCount: number;
  lyricsList: string[] | string;
  songFileUrl: string;
  songId: number;
  songImageUrl: string;
  songKeywordList: string[] | string;
  songKeywordKoreanList: string[] | string;
  songName: string;
  views: number;
}

export interface StoryImageRes {
  startTime: string;
  content: string;
  imageURL: string | null;
}
export interface SongLikeRequest {
  userId: number;
  songId: number;
}
type RefSongDetail = SongDetail | null;
type User = NullUser | string | null;
type RefUser = User | Ref;
type RefSong = Song | null;
type RefStoryImageRes = StoryImageRes | null;

export interface AudioPlayerComponent {
  option_: import("vue").Ref<{
    src: string;
    title?: string | undefined;
    autoPlay?: boolean | undefined;
    coverImage?: string | undefined;
    coverRotate?: boolean | undefined;
    progressBarColor?: string | undefined;
    indicatorColor?: string | undefined;
  }>;
  onAudioEnded: () => void;
  onAudioPlay: () => void;
  onAudioPause: () => void;
  onLoadMetaData: (e: any) => void;
  onTimeUpdate: (event: any) => void;
  play: () => void;
  pause: () => void;
  togglePlayer: () => void;
  formatSecond: (second: number) => string;
  handleProgressPanStart: (event: any) => void;
  handleProgressPanEnd: (event: any) => void;
  handleProgressPanMove: (event: any) => void;
  handleClickProgressWrap: (event: any) => void;
  audioProgressWrap: import("vue").Ref<any>;
  audioProgressPoint: import("vue").Ref<any>;
  audioProgress: import("vue").Ref<any>;
  IconPlay: string;
  IconPause: string;
  CoverImageDefault: string;
  isPlaying: import("vue").Ref<boolean>;
  isDragging: import("vue").Ref<boolean>;
  currentTime: import("vue").Ref<number>;
  totalTime: import("vue").Ref<number>;
  totalTimeStr: import("vue").Ref<string>;
  audioPlayer: import("vue").Ref<any>;
}
export type RefAudioPlayerComponent = import("vue").Ref<AudioPlayerComponent>;

export interface AudioPlayerComponent2 {
  option_: {
    src: string;
    title?: string | undefined;
    autoPlay?: boolean | undefined;
    coverImage?: string | undefined;
    coverRotate?: boolean | undefined;
    progressBarColor?: string | undefined;
    indicatorColor?: string | undefined;
  };
  onAudioEnded: () => void;
  onAudioPlay: () => void;
  onAudioPause: () => void;
  onLoadMetaData: (e: any) => void;
  onTimeUpdate: (event: any) => void;
  play: () => void;
  pause: () => void;
  togglePlayer: () => void;
  formatSecond: (second: number) => string;
  handleProgressPanStart: (event: any) => void;
  handleProgressPanEnd: (event: any) => void;
  handleProgressPanMove: (event: any) => void;
  handleClickProgressWrap: (event: any) => void;
  audioProgressWrap: any;
  audioProgressPoint: any;
  audioProgress: any;
  IconPlay: string;
  IconPause: string;
  CoverImageDefault: string;
  isPlaying: boolean;
  isDragging: boolean;
  currentTime: number;
  totalTime: number;
  totalTimeStr: string;
  audioPlayer: any;
}
