import { Moment } from 'moment';
import { IUnvan } from 'app/shared/model/unvan.model';
import { ICalisan } from 'app/shared/model/calisan.model';
import { IEkip } from 'app/shared/model/ekip.model';

export const enum CalisanTuru {
    MAASLI = 'MAASLI',
    GOTURU = 'GOTURU',
    YEVMIYE = 'YEVMIYE',
    TASERE = 'TASERE',
    GECICI = 'GECICI'
}

export interface ICalisan {
    id?: number;
    tckimlikno?: number;
    ad?: string;
    soyad?: string;
    eposta?: string;
    telefon?: string;
    dogumTarihi?: Moment;
    girisTarihi?: Moment;
    cikisTarihi?: Moment;
    ucret?: number;
    calisanTuru?: CalisanTuru;
    unvan?: IUnvan;
    sorumlu?: ICalisan;
    ekips?: IEkip[];
}

export class Calisan implements ICalisan {
    constructor(
        public id?: number,
        public tckimlikno?: number,
        public ad?: string,
        public soyad?: string,
        public eposta?: string,
        public telefon?: string,
        public dogumTarihi?: Moment,
        public girisTarihi?: Moment,
        public cikisTarihi?: Moment,
        public ucret?: number,
        public calisanTuru?: CalisanTuru,
        public unvan?: IUnvan,
        public sorumlu?: ICalisan,
        public ekips?: IEkip[]
    ) {}
}
