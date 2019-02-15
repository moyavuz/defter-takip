import { Moment } from 'moment';
import { ICalisan } from 'app/shared/model/calisan.model';
import { IUnvan } from 'app/shared/model/unvan.model';
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
    eposta?: string;
    telefon?: string;
    iban?: string;
    dogumTarihi?: Moment;
    girisTarihi?: Moment;
    cikisTarihi?: Moment;
    ucret?: number;
    calisanTuru?: CalisanTuru;
    yonetici?: ICalisan;
    unvan?: IUnvan;
    ekips?: IEkip[];
}

export class Calisan implements ICalisan {
    constructor(
        public id?: number,
        public tckimlikno?: number,
        public ad?: string,
        public eposta?: string,
        public telefon?: string,
        public iban?: string,
        public dogumTarihi?: Moment,
        public girisTarihi?: Moment,
        public cikisTarihi?: Moment,
        public ucret?: number,
        public calisanTuru?: CalisanTuru,
        public yonetici?: ICalisan,
        public unvan?: IUnvan,
        public ekips?: IEkip[]
    ) {}
}
